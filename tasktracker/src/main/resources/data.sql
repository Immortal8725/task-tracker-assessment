-- Seed users (ignore if already exist - H2 supports MERGE)
MERGE INTO users (username, email, password_hash, role) KEY (username)
    VALUES
    ('admin', 'admin@example.com', '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN'),
    ('john', 'john@example.com', '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'USER');

-- Seed some tasks
MERGE INTO tasks (id, title, description, status, due_date, created_date, assigned_user_id) KEY (id)
    VALUES
    (1, 'Implement authentication', 'JWT + refresh tokens', 'COMPLETED', NULL, CURRENT_TIMESTAMP,
    (SELECT id FROM users WHERE username = 'admin')),
    (2, 'Build task CRUD', 'Full REST endpoints with filtering', 'IN_PROGRESS', '2025-12-31 23:59:59', CURRENT_TIMESTAMP,
    (SELECT id FROM users WHERE username = 'john')),
    (3, 'Add overdue scheduler', 'Auto-mark overdue tasks', 'NEW', '2025-12-10 23:59:59', CURRENT_TIMESTAMP,
    (SELECT id FROM users WHERE username = 'john'));