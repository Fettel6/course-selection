-- 创建Mysql数据库和表
Create Database If Not Exists app_springboot Character Set UTF8;
use app_springboot;

-- 创建管理员表
create table tb_admin (
    id varchar(32) comment '管理员主键',
    username varchar(20) comment '用户名',
    password varchar(20) comment '密码',
    name varchar(12) comment '姓名',
    tele varchar(11) comment '电话',
    createtime datetime comment '创建时间',
    PRIMARY KEY (`id`)
)comment '管理员表';

CREATE TABLE `tb_student` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password` varchar(20) DEFAULT NULL COMMENT '密码',
  `studentno` varchar(20) DEFAULT NULL COMMENT '学号',
  `name` varchar(12) DEFAULT NULL COMMENT '姓名',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `classname` varchar(30) DEFAULT NULL COMMENT '班级',
  `nativeplace` varchar(50) DEFAULT NULL COMMENT '籍贯',
  `tele` varchar(11) DEFAULT NULL COMMENT '电话',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

CREATE TABLE `tb_course` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `courseno` varchar(20) DEFAULT NULL COMMENT '课程号',
  `name` varchar(50) DEFAULT NULL COMMENT '课程名称',
  `credit` decimal(3,1) DEFAULT NULL COMMENT '学分',
  `teacher` varchar(20) DEFAULT NULL COMMENT '任课教师',
  `description` varchar(500) DEFAULT NULL COMMENT '课程描述',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- 创建选课表
CREATE TABLE `tb_course_selection` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `student_id` varchar(50) NOT NULL COMMENT '学生ID，关联tb_student表',
  `course_id` varchar(50) NOT NULL COMMENT '课程ID，关联tb_course表',
  `score` decimal(5,2) DEFAULT NULL COMMENT '成绩',
  `evaluation` varchar(500) DEFAULT NULL COMMENT '课程评价',
  `selection_time` datetime DEFAULT NULL COMMENT '选课时间',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_student_course` (`student_id`, `course_id`) COMMENT '学生和课程的唯一约束，防止重复选课',
  KEY `idx_student_id` (`student_id`),
  KEY `idx_course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='选课表';

-- 插入测试数据
-- 管理员表测试数据
INSERT INTO tb_admin (id, username, password, name, tele, createtime) VALUES
('1', 'admin1', '123456', '张三', '13800138001', NOW()),
('2', 'admin2', '123456', '李四', '13800138002', NOW()),
('3', 'admin3', '123456', '王五', '13800138003', NOW()),
('4', 'admin4', '123456', '赵六', '13800138004', NOW()),
('5', 'admin5', '123456', '钱七', '13800138005', NOW());

-- 学生表测试数据
INSERT INTO tb_student (id, username, password, studentno, name, gender, classname, nativeplace, tele, createtime) VALUES
('1', 'student1', '123456', '2024001', '小明', '男', '计算机科学与技术1班', '北京', '13900139001', NOW()),
('2', 'student2', '123456', '2024002', '小红', '女', '计算机科学与技术1班', '上海', '13900139002', NOW()),
('3', 'student3', '123456', '2024003', '小刚', '男', '计算机科学与技术2班', '广州', '13900139003', NOW()),
('4', 'student4', '123456', '2024004', '小丽', '女', '计算机科学与技术2班', '深圳', '13900139004', NOW()),
('5', 'student5', '123456', '2024005', '小强', '男', '软件工程1班', '杭州', '13900139005', NOW()),
('6', 'student6', '123456', '2024006', '小花', '女', '软件工程1班', '南京', '13900139006', NOW()),
('7', 'student7', '123456', '2024007', '小明', '男', '软件工程2班', '武汉', '13900139007', NOW()),
('8', 'student8', '123456', '2024008', '小红', '女', '软件工程2班', '成都', '13900139008', NOW());

-- 课程表测试数据
INSERT INTO tb_course (id, courseno, name, credit, teacher, description, createtime) VALUES
('1', 'C001', '高等数学', 4.0, '张老师', '高等数学基础课程，包括微积分、线性代数等内容', NOW()),
('2', 'C002', '大学英语', 3.0, '李老师', '大学英语课程，提高学生的英语听、说、读、写能力', NOW()),
('3', 'C003', '计算机基础', 3.5, '王老师', '计算机基础课程，包括计算机原理、操作系统等内容', NOW()),
('4', 'C004', '数据结构', 4.0, '赵老师', '数据结构课程，学习各种数据结构的原理和应用', NOW()),
('5', 'C005', '算法设计', 3.5, '钱老师', '算法设计课程，学习各种算法的设计和分析', NOW()),
('6', 'C006', '数据库原理', 4.0, '孙老师', '数据库原理课程，学习数据库的设计和管理', NOW()),
('7', 'C007', '计算机网络', 3.5, '周老师', '计算机网络课程，学习网络协议和网络管理', NOW()),
('8', 'C008', '软件工程', 4.0, '吴老师', '软件工程课程，学习软件开发的方法和工具', NOW());

-- 选课表测试数据
INSERT INTO tb_course_selection (id, student_id, course_id, score, evaluation, selection_time, createtime) VALUES
('1', '1', '1', 85.5, '课程内容丰富，老师讲解清晰', NOW() - INTERVAL 1 MONTH, NOW() - INTERVAL 1 MONTH),
('2', '1', '2', 90.0, '英语老师教学方法新颖，很有帮助', NOW() - INTERVAL 1 MONTH, NOW() - INTERVAL 1 MONTH),
('3', '1', '3', 88.0, '计算机基础课程内容全面', NOW() - INTERVAL 1 MONTH, NOW() - INTERVAL 1 MONTH),
('4', '2', '1', 92.0, '高等数学老师讲解详细，容易理解', NOW() - INTERVAL 1 MONTH, NOW() - INTERVAL 1 MONTH),
('5', '2', '4', 86.0, '数据结构课程很实用', NOW() - INTERVAL 1 MONTH, NOW() - INTERVAL 1 MONTH),
('6', '3', '2', 89.5, '英语课程很有趣', NOW() - INTERVAL 1 MONTH, NOW() - INTERVAL 1 MONTH),
('7', '3', '5', 91.0, '算法设计课程挑战性强', NOW() - INTERVAL 1 MONTH, NOW() - INTERVAL 1 MONTH),
('8', '4', '3', 87.0, '计算机基础课程很重要', NOW() - INTERVAL 1 MONTH, NOW() - INTERVAL 1 MONTH),
('9', '4', '6', 90.5, '数据库原理课程很实用', NOW() - INTERVAL 1 MONTH, NOW() - INTERVAL 1 MONTH),
('10', '5', '4', 88.5, '数据结构课程内容丰富', NOW() - INTERVAL 1 MONTH, NOW() - INTERVAL 1 MONTH);
