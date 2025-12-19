-- Seed users with BCrypt passwords (use real hashes!)
INSERT INTO users (username, email, password_hash, role)
VALUES
    ('admin', 'admin@example.com', '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN'),
    ('john', 'john@example.com', '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'USER')
    ON DUPLICATE KEY UPDATE username = username;  -- H2-compatible alternative

-- Seed some tasks
INSERT INTO tasks (title, description, status, due_date, created_date, assigned_user_id)
VALUES
    ('Implement authentication', 'JWT + refresh tokens', 'COMPLETED', NULL, CURRENT_TIMESTAMP,
     (SELECT id FROM users WHERE username = 'admin')),
    ('Build task CRUD', 'Full REST endpoints with filtering', 'IN_PROGRESS', '2025-12-25 23:59:59', CURRENT_TIMESTAMP,
     (SELECT id FROM users WHERE username = 'john')),
    ('Fix overdue scheduler', 'Mark tasks as OVERDUE automatically', 'NEW', '2025-12-10 23:59:59', CURRENT_TIMESTAMP,
     (SELECT id FROM users WHERE username = 'john'));