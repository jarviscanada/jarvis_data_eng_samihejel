psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# Check the number of arguments
if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)

#parse server CPU and memory usage data using bash scripts
cpu_idle=$(vmstat | awk '{print $15}'| tail -n1 | xargs)
cpu_kernel=$(vmstat | awk '{print $14}'| tail -n1 | xargs)
memory_free=$(echo "$vmstat_mb" | awk '{print $4}'| tail -n1 | xargs)
disk_io=$(vmstat -d | awk '{print $10}'| tail -n1 | xargs)
disk_available=$(df -BM / | awk '{print $4}'| tail -n1  | sed 's/M//')

#Current time in `2019-11-26 14:40:19` UTC format
timestamp=$(date "+%Y-%m-%d %T")

# Subquery to find matching id in host_info table
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";
#execute the INSERT statement
# Construct the INSERT statement
insert_stmt="INSERT INTO host_usage(timestamp, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available) VALUES ('$timestamp', $host_id, $memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available)"

#set up env var for pql cmd
export PGPASSWORD=$psql_password
#Insert date into a database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?