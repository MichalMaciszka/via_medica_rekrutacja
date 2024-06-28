CREATE DATABASE via;
GO

CREATE LOGIN dev WITH PASSWORD = 'YourStrong@Passw0rd';
GO

EXEC master..sp_addsrvrolemember @loginame = 'dev', @rolename = 'sysadmin'
GO

USE via;
GO

CREATE SCHEMA SIT;
GO

ALTER USER dev WITH DEFAULT_SCHEMA = SIT;
GO

CREATE TABLE [SIT].[Serwisant](
    Identyfikator int NOT NULL IDENTITY(1,1),
    Nazwisko nvarchar(250) NOT NULL,  
    Aktywny tinyint NOT NULL DEFAULT 1,
    Email nvarchar(50) NOT NULL
    CONSTRAINT PK_Serwisant PRIMARY KEY (Identyfikator)
);

-- Tabela Obszar
CREATE TABLE [Obszar](
    Identyfikator int NOT NULL IDENTITY(1,1),
    Nazwa nvarchar(250) NOT NULL, 
    Aktywny tinyint NOT NULL DEFAULT 1,
    IdentyfikatorSerwisanta int,
    CONSTRAINT PK_Obszar PRIMARY KEY (Identyfikator),
    CONSTRAINT FK_Obszar_Serwisant FOREIGN KEY (IdentyfikatorSerwisanta)
        REFERENCES [SIT].[Serwisant](Identyfikator)
);

-- Tabela Działanie
CREATE TABLE [SIT].[Działanie](
    Identyfikator int NOT NULL IDENTITY(1,1),
    IdentyfikatorSerwisanta int,
    IdentyfikatorObszaru int,
    OpisDzialania nvarchar(255),
    PlanowanyCzas int,
    CONSTRAINT PK_Działanie PRIMARY KEY (Identyfikator),
    CONSTRAINT FK_Działanie_Serwisant FOREIGN KEY (IdentyfikatorSerwisanta)
        REFERENCES [SIT].[Serwisant](Identyfikator),
    CONSTRAINT FK_Działanie_Obszar FOREIGN KEY (IdentyfikatorObszaru)
        REFERENCES [Obszar](Identyfikator)
);
GO

-- Widok lista obszarów z przypisanymi serwisantami
CREATE VIEW [vw_ObszaryZSerwisantami] AS
SELECT 
    o.Identyfikator AS ObszarId,
    o.Nazwa AS ObszarNazwa,
    s.Identyfikator AS SerwisantId,
    s.Nazwisko AS SerwisantNazwisko
FROM 
    [Obszar] o
LEFT JOIN 
    [SIT].[Serwisant] s ON o.IdentyfikatorSerwisanta = s.Identyfikator
WHERE
    o.Aktywny = 1 AND s.Aktywny = 1;
GO

-- Widok lista zaplanowanych działań dla serwisanta
CREATE VIEW [vw_DzialaniaDlaSerwisanta] AS
SELECT 
    d.Identyfikator AS DzialanieId,
    d.OpisDzialania,
    d.PlanowanyCzas,
    s.Identyfikator AS SerwisantId,
    s.Nazwisko AS SerwisantNazwisko,
    o.Identyfikator AS ObszarId,
    o.Nazwa AS ObszarNazwa
FROM 
    [SIT].[Działanie] d
LEFT JOIN
    [SIT].[Serwisant] s ON d.IdentyfikatorSerwisanta = s.Identyfikator
LEFT JOIN
    [Obszar] o ON d.IdentyfikatorObszaru = o.Identyfikator
WHERE 
    s.Aktywny = 1 AND o.Aktywny = 1;

GO

CREATE INDEX IX_Obszar_IdentyfikatorSerwisanta ON [Obszar](IdentyfikatorSerwisanta);
CREATE INDEX IX_Dzialanie_IdentyfikatorSerwisanta ON [SIT].[Działanie](IdentyfikatorSerwisanta);
CREATE INDEX IX_Dzialanie_IdentyfikatorObszaru ON [SIT].[Działanie](IdentyfikatorObszaru);
GO

-- przykładowe dane
USE via;
GO

-- Dodanie przykładowych serwisantów
INSERT INTO [SIT].[Serwisant] (Nazwisko, Aktywny, Email) VALUES
('Kowalski', 1, 'kowalski@example.com'),
('Nowak', 1, 'nowak@example.com'),
('Wiśniewski', 0, 'wisniewski@example.com');  -- Serwisant nieaktywny

-- Dodanie przykładowych obszarów
INSERT INTO [Obszar] (Nazwa, Aktywny, IdentyfikatorSerwisanta) VALUES
('Warszawa', 1, 1),
('Kraków', 1, 2),
('Gdańsk', 1, NULL),  -- Obszar bez przypisanego serwisanta
('Wrocław', 0, 1);    -- Obszar nieaktywny

-- Dodanie przykładowych działań
INSERT INTO [SIT].[Działanie] (IdentyfikatorSerwisanta, IdentyfikatorObszaru, OpisDzialania, PlanowanyCzas) VALUES
(1, 1, 'Naprawa sieci', 120),
(2, 2, 'Instalacja oprogramowania', 60),
(1, 1, 'Wymiana sprzętu', 90),
(1, 4, 'Konserwacja systemu', 30);  -- Działanie w nieaktywnym obszarze
