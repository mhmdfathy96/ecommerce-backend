-- SQL script to add unique constraint on username column in users table
-- Run this script if the constraint doesn't exist in your database

-- Check if constraint already exists and drop it if needed (optional, for recreation)
-- ALTER TABLE users DROP CONSTRAINT IF EXISTS uk_username;

-- Add unique constraint on username column
ALTER TABLE users ADD CONSTRAINT uk_username UNIQUE (username);

-- Verify the constraint was created
-- You can check with: SELECT constraint_name FROM information_schema.table_constraints WHERE table_name = 'users' AND constraint_type = 'UNIQUE';
