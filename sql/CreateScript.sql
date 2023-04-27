USE locodocu;

DROP TABLE IF EXISTS UserRoleRelation;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Roles;

CREATE TABLE [Users] (
    [Id] INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
    [Name] NVARCHAR(255) NOT NULL,
    [Email] NVARCHAR(255) NOT NULL UNIQUE,
    [Phone] VARCHAR(20),
    [Password] VARCHAR(MAX),
    [DisabledAt] DATETIME2 DEFAULT NULL
);

CREATE TABLE [Roles] (
    [Id] INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
    [Name] NVARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE [UserRoleRelation] (
    [Id] INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
    [UserId] INT FOREIGN KEY REFERENCES [Users](Id),
    [RoleId] INT FOREIGN KEY REFERENCES [Roles](Id)
);

-- Insert data
INSERT INTO [Roles] ([Name]) VALUES 
    ('admin'), -- Id 1
    ('project-manager'), -- Id 2
    ('technician'), -- Id 3
    ('salesperson'); -- Id 4

-- Insert fake data
INSERT INTO [Users] ([Name], [Email], [Phone], [Password]) VALUES ('admin', 'admin@wuav.dk', '12345678', '$2a$12$UcZasGP.JlW6Og/Nwd2CRe8.HuJ9vG/pVCDLErIPxrUpKY9/Ny1kC'); -- id 1
INSERT INTO [Users] ([Name], [Email], [Phone], [Password]) VALUES ('pm', 'pm@wuav.dk', '12345678', '$2a$12$UcZasGP.JlW6Og/Nwd2CRe8.HuJ9vG/pVCDLErIPxrUpKY9/Ny1kC'); -- id 2
INSERT INTO [Users] ([Name], [Email], [Phone], [Password]) VALUES ('tech', 'tech@wuav.dk', '12345678', '$2a$12$UcZasGP.JlW6Og/Nwd2CRe8.HuJ9vG/pVCDLErIPxrUpKY9/Ny1kC'); -- id 3
INSERT INTO [Users] ([Name], [Email], [Phone], [Password]) VALUES ('sale', 'sale@wuav.dk', '12345678', '$2a$12$UcZasGP.JlW6Og/Nwd2CRe8.HuJ9vG/pVCDLErIPxrUpKY9/Ny1kC'); -- id 4

INSERT INTO [UserRoleRelation] ([UserId], [RoleId]) VALUES 
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4);