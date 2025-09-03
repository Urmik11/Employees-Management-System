-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 02, 2025 at 09:33 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `emp`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `resignEmployee` (IN `in_emp_id` INT, IN `in_dept_id` INT, IN `in_role_id` INT, IN `in_first_name` VARCHAR(20), IN `in_last_name` VARCHAR(20), IN `in_phone` VARCHAR(10), IN `in_salary` INT, IN `in_hire_date` DATE, IN `in_resign_date` DATE)   BEGIN
	insert into resigned_employees(emp_id,dept_id,role_id,first_name,last_name,phone,salary,hire_date,resign_date) VALUES 		(in_emp_id,in_dept_id,in_role_id,in_first_name,in_last_name,in_phone,in_salary,in_hire_date,in_resign_date);
	DELETE FROM employees WHERE emp_id=in_emp_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `viewDept` ()   BEGIN
	SELECT * FROM departments;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `departments`
--

CREATE TABLE `departments` (
  `dept_id` int(11) NOT NULL,
  `dept_name` varchar(50) NOT NULL,
  `creation_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `departments`
--

INSERT INTO `departments` (`dept_id`, `dept_name`, `creation_date`) VALUES
(101, 'Human Resources (HR)', '2023-03-06'),
(102, 'Finance', '2023-03-06'),
(103, 'Sales', '2023-03-06'),
(104, 'Marketing', '2023-03-06'),
(105, 'Information Technology (IT)', '2023-03-06'),
(106, 'Customer Service', '2023-03-06'),
(107, 'Product Development', '2023-03-06'),
(108, 'Legal and Compliance', '2023-03-06');

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `emp_id` int(11) NOT NULL,
  `dept_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `salary` int(11) NOT NULL,
  `hire_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`emp_id`, `dept_id`, `role_id`, `first_name`, `last_name`, `phone`, `salary`, `hire_date`) VALUES
(16, 102, 9, 'Utsav', 'Patel', '8141190345', 40000, '2024-07-18'),
(17, 101, 1, 'Krunal', 'Chavda', '9876543210', 67500, '2024-08-31'),
(18, 104, 20, 'Varun', 'Jain', '9988670543', 30000, '2025-08-02');

-- --------------------------------------------------------

--
-- Table structure for table `resigned_employees`
--

CREATE TABLE `resigned_employees` (
  `emp_id` int(11) NOT NULL,
  `dept_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `salary` int(11) NOT NULL,
  `hire_date` date NOT NULL,
  `resign_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `resigned_employees`
--

INSERT INTO `resigned_employees` (`emp_id`, `dept_id`, `role_id`, `first_name`, `last_name`, `phone`, `salary`, `hire_date`, `resign_date`) VALUES
(15, 105, 24, 'Urmik', 'Dobariya', '9227083370', 35000, '2024-06-01', '2024-08-31');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `role_id` int(10) NOT NULL,
  `dept_id` int(10) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `role_salary` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `dept_id`, `role_name`, `role_salary`) VALUES
(1, 101, 'HR Manager', 50000),
(2, 101, 'Recruiter', 25000),
(3, 101, 'Tranking Specialist', 30000),
(4, 101, 'HR Cordinator', 25000),
(5, 101, 'Benefits Analyst', 30000),
(6, 102, 'Financial Analyst', 40000),
(7, 102, 'Accountant', 25000),
(8, 102, 'Payroll Specialist', 24000),
(9, 102, 'Finance Manager', 40000),
(10, 102, 'Controller', 40000),
(11, 103, 'Sales Resprectative', 30000),
(12, 103, 'Account Executive', 35000),
(13, 103, 'Sales Manager', 40000),
(14, 103, 'Business Devlopments Manager', 35000),
(15, 103, 'Sales Analyst', 30000),
(16, 104, 'Marketing Manager', 40000),
(17, 104, 'Content Strategist', 30000),
(18, 104, 'Social Media Specialist', 25000),
(19, 104, 'SEO Specialist', 30000),
(20, 104, 'Marketing Analyst', 30000),
(21, 105, 'IT Support Specialist', 25000),
(22, 105, 'System Administrator', 30000),
(23, 105, 'Network Engineer', 30000),
(24, 105, 'Software Developer', 35000),
(25, 105, 'IT Project Manager', 40000),
(26, 106, 'Customer Service Respresentative', 25000),
(27, 106, 'Technical Support Specialist', 25000),
(28, 106, 'Customer Success Manager', 30000),
(29, 106, 'Call Center Agent', 30000),
(30, 106, 'Service Coordinator', 25000),
(31, 107, 'Project Manager ', 35000),
(32, 107, 'Research Scientist ', 30000),
(33, 107, 'Product Designer', 30000),
(34, 107, 'Development Engineer', 35000),
(35, 107, 'Quality Assurance Tester', 30000),
(36, 108, 'Legal Counsel', 35000),
(38, 108, 'Compliance Officer', 30000),
(39, 108, 'Contract Specialist', 30000),
(40, 108, 'Risk Manager', 35000),
(43, 101, 'Hr', 34500);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `departments`
--
ALTER TABLE `departments`
  ADD PRIMARY KEY (`dept_id`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`emp_id`);

--
-- Indexes for table `resigned_employees`
--
ALTER TABLE `resigned_employees`
  ADD PRIMARY KEY (`emp_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `emp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `resigned_employees`
--
ALTER TABLE `resigned_employees`
  MODIFY `emp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `role_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
