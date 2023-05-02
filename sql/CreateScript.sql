USE locodocu;

DROP TABLE IF EXISTS UserRoleRelation;
DROP TABLE IF EXISTS ProjectUserRelation;
DROP TABLE IF EXISTS Roles;

DROP TABLE IF EXISTS DocumentationTextField;
DROP TABLE IF EXISTS ProjectDocumentationRelation;
DROP TABLE IF EXISTS Documentation;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Users;

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

CREATE TABLE [Customer] (
    [Id] INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
    [Name] VARCHAR(255) NOT NULL,
    [Email] VARCHAR(255) NOT NULL,
    [Phone] VARCHAR(20),
    [Address] VARCHAR(255) NOT NULL,
);

CREATE TABLE [Project] (
    [Id] INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
    [Name] VARCHAR(255) NOT NULL,
    [CreatedAt] DATETIME2 DEFAULT GETUTCDATE(),
    [CustomerId] INT FOREIGN KEY REFERENCES [Customer](Id) NOT NULL
);

CREATE TABLE [ProjectUserRelation] (
    [Id] INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
    [ProjectId] INT FOREIGN KEY REFERENCES [Project](Id) NOT NULL,
    [UserId] INT FOREIGN KEY REFERENCES [Users](Id) NOT NULL
);

CREATE TABLE [Documentation] (
    [Id] INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
    [Name] VARCHAR(255) NOT NULL
);

CREATE TABLE [ProjectDocumentationRelation] (
    [Id] INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
    [ProjectId] INT FOREIGN KEY REFERENCES [Project](Id) NOT NULL,
    [DocumentationId] INT FOREIGN KEY REFERENCES [Documentation](Id) NOT NULL
);

-- Documentation nodes
CREATE TABLE [DocumentationTextField] (
    [Id] INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
    [Text] NVARCHAR(MAX),
    [DocumentationId] INT FOREIGN KEY REFERENCES [Documentation](Id) NOT NULL
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

-- Create fake project and documentation
INSERT INTO [Customer] ([Name], [Phone], [Email], [Address]) VALUES ('Mærsk', '42424242', 'noreply@maersk.dk', 'Mærsk vej, 42, 4242 Fantasiby'); -- Id 1

INSERT INTO [Project] ([Name], [CustomerId]) VALUES ('Det store blå skib', 1); -- Id 1

INSERT INTO [ProjectUserRelation] ([ProjectId], [UserId]) VALUES (1, 3);

INSERT INTO [Documentation] ([Name]) VALUES ('Broen'); -- Id 1
INSERT INTO [ProjectDocumentationRelation] ([ProjectId], [DocumentationId]) VALUES (1, 1);
INSERT INTO [DocumentationTextField] ([DocumentationId], [Text]) VALUES (1, 'We have made the best audio system to scare away pirates arrrrrr.');

-- Fake documentation
INSERT INTO [Customer] ([Name], [Phone], [Email], [Address]) VALUES ('Lego', '78491494', 'noreply@lego.dk', 'Lego vej, 420, 7190 Billund'); -- Id 2

INSERT INTO [Project] ([Name], [CustomerId]) VALUES ('Lego House', 2); -- Id 2

INSERT INTO [ProjectUserRelation] ([ProjectId], [UserId]) VALUES (2, 3);

INSERT INTO [Documentation] ([Name]) VALUES ('Receptionen'); -- Id 2
INSERT INTO [ProjectDocumentationRelation] ([ProjectId], [DocumentationId]) VALUES (2, 2);
INSERT INTO [DocumentationTextField] ([DocumentationId], [Text]) VALUES (2, 'Many lights');