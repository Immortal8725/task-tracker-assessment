-- Passwords are BCrypt for "password"
INSERT INTO users (username, email, password_hash, role)
VALUES
    ('admin', 'admin@example.com', '$2a$12$YOUR_BCRYPT_HASH_FOR_password123', 'ADMIN'),
    ('john', 'john@example.com', '$2a$12$YOUR_BCRYPT_HASH_FOR_password123', 'USER')
    ON CONFLICT (username) DO NOTHING;

INSERT INTO tasks (title, description, status, due_date, created_date, assigned_user_id)
VALUES
    ('Finish report', 'Q4 financial report', 'IN_PROGRESS', '2025-12-20 23:59:59', '2025-12-01 10:00:00', 2),
    ('Fix bug #123', 'Critical login issue', 'NEW', '2025-12-10 23:59:59', '2025-12-05 09:00:00', 2),  -- already overdue
    ('Code review', 'Review PR #456', 'COMPLETED', '2025-12-15 23:59:59', '2025-12-10 14:00:00', 1)
    ON CONFLICT DO NOTHING;