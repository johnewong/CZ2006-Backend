DROP TABLE IF EXISTS `appointment`;

CREATE TABLE `appointment` (
  `AppointmentID` bigint(20) NOT NULL,
  `AppointmentNumber` varchar(255) NOT NULL,
  `AppointmentDate` date NOT NULL,
  `AppointmentStartTime` varchar(5)  NOT NULL,
  `AppointmentEndTime` varchar(5)  NOT NULL,
  `PatientID` bigint(20) NOT NULL,
  `PatientName` varchar(255) NOT NULL,
  `Status` tinyint(5) NOT NULL,
  `DentistID` bigint(20) NOT NULL,
  `DentalID` bigint(20) NOT NULL,
  `TreatmentID` bigint(20) NOT NULL,
  `CreatedBy` bigint(20) NOT NULL,
  `CreatedDate` datetime NOT NULL,
  `UpdatedBy` bigint(20) DEFAULT NULL,
  `UpdatedDate` datetime DEFAULT NULL,
  `IsDeleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`AppointmentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `dental`;
CREATE TABLE `dental` (
  `DentalID` bigint(20) NOT NULL,
  `DentalName` varchar(255) NOT NULL,
  `DentalDescription` varchar(3000) DEFAULT NULL,
  `DentalAddress` varchar(255) DEFAULT NULL,
  `OperatingHourStart` varchar(5)  NOT NULL,
  `OperatingHourEnd` varchar(5)  NOT NULL,
  `CreatedBy` bigint(20) NOT NULL,
  `CreatedDate` datetime NOT NULL,
  `UpdatedBy` bigint(20) DEFAULT NULL,
  `UpdatedDate` datetime DEFAULT NULL,
  `IsDeleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`DentalID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `dental_treatment`;
CREATE TABLE `dental_treatment` (
  `Dental_TreatmentID` bigint(20) NOT NULL,
  `DentalID` bigint(20) NOT NULL,
  `TreatmentID` bigint(20) NOT NULL,
  `PerSessionDuration` float NOT NULL,
  `CreatedBy` bigint(20) NOT NULL,
  `CreatedDate` datetime NOT NULL,
  `UpdatedBy` bigint(20) DEFAULT NULL,
  `UpdatedDate` datetime DEFAULT NULL,
  `IsDeleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`Dental_TreatmentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `dentist`;
CREATE TABLE `dentist` (
  `DentistID` bigint(20) NOT NULL,
  `DentistName` varchar(255) NOT NULL,
  `DentalID` bigint(20) NOT NULL,
  `DentistDescription` varchar(3000) DEFAULT NULL,
  `Gender` varchar(10) NOT NULL,
  `IsOnLeave` tinyint(4) DEFAULT NULL,
  `LeaveStartDate` date DEFAULT NULL,
  `LeaveEndDate` date DEFAULT NULL,
  `CreatedBy` bigint(20) NOT NULL,
  `CreatedDate` datetime NOT NULL,
  `UpdatedBy` bigint(20) DEFAULT NULL,
  `UpdatedDate` datetime DEFAULT NULL,
  `IsDeleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`DentistID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `dentist_working_schedule`;
CREATE TABLE `dentist_working_schedule` (
  `DentistWorkingScheduleID` bigint(20) NOT NULL,
  `DentistID` bigint(20) NOT NULL,
  `DayOfWeek` int(1) NOT NULL,
  `StartTime` varchar(5) NOT NULL,
  `EndTime` varchar(5)  NOT NULL,
  `BreakStartTime` varchar(5)  NOT NULL,
  `BreakEndTime` varchar(5)  NOT NULL,
  `CreatedBy` bigint(20) NOT NULL,
  `CreatedDate` datetime NOT NULL,
  `UpdatedBy` bigint(20) DEFAULT NULL,
  `UpdatedDate` datetime DEFAULT NULL,
  `IsDeleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`DentistWorkingScheduleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `treatment`;
CREATE TABLE `treatment` (
  `TreatmentID` bigint(20) NOT NULL,
  `TreatmentName` varchar(255) NOT NULL,
  `CreatedBy` bigint(20) NOT NULL,
  `CreatedDate` datetime NOT NULL,
  `UpdatedBy` bigint(20) DEFAULT NULL,
  `UpdatedDate` datetime DEFAULT NULL,
  `IsDeleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`TreatmentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `UserID` bigint(20) NOT NULL,
  `UserName` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `BirthDate` datetime NOT NULL,
  `EmailAddress` varchar(255) NOT NULL,
  `ContactNumber` varchar(20) NOT NULL,
  `Gender` tinyint(1) DEFAULT NULL,
  `ICNumber` varchar(15) NOT NULL,
  `UserType` tinyint(5) DEFAULT NULL,
  `CreatedBy` bigint(20) NOT NULL,
  `CreatedDate` datetime NOT NULL,
  `UpdatedBy` bigint(20) DEFAULT NULL,
  `UpdatedDate` datetime DEFAULT NULL,
  `IsDeleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
