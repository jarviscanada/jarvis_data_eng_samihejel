-- Create host_info table
CREATE TABLE IF NOT EXISTS host_info (
    id SERIAL PRIMARY KEY,
    hostname VARCHAR(255) NOT NULL,
    cpu_number INT NOT NULL,
    cpu_architecture VARCHAR(255) NOT NULL,
    cpu_model VARCHAR(255) NOT NULL,
    cpu_mhz FLOAT NOT NULL,
    l2_cache INT NOT NULL,
    timestamp TIMESTAMP,
    total_mem INT,
    CONSTRAINT host_info_unique_hostname UNIQUE (hostname)
);

-- Create a host_usage table
CREATE TABLE IF NOT EXISTS host_usage (
    "timestamp" TIMESTAMP NOT NULL,
    host_id SERIAL NOT NULL,
    memory_free INT NOT NULL, -- in MB
    cpu_idle INT NOT NULL, -- in percentage
    cpu_kernel INT NOT NULL, -- in percentage
    disk_io INT NOT NULL, -- number of disk I/O
    disk_available INT NOT NULL, -- in MB, root directory available disk
    CONSTRAINT host_usage_host_info_fk FOREIGN KEY (host_id) REFERENCES host_info(id)
);