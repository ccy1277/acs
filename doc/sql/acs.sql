# 创建数据库
CREATE DATABASE acs  CHARACTER SET 'utf8';
# 使用数据库
USE acs;

# 用户表
CREATE TABLE user(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(64) NOT NUll UNIQUE COMMENT '用户名',
    password VARCHAR(300) NOT NULL COMMENT '用户密码',
    nickname VARCHAR(32) COMMENT '昵称',
    profile VARCHAR(200) COMMENT '头像',
    email VARCHAR(100) COMMENT '邮箱',
    create_time DATETIME COMMENT '注册时间',
    login_time DATETIME COMMENT '最后登录时间',
    status INT DEFAULT 1 COMMENT '账号状态 1: 激活 0: 禁用',
    description LONGTEXT COMMENT '描述'
);

# 角色表
CREATE TABLE role(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL UNIQUE COMMENT '角色名',
    description LONGTEXT COMMENT '角色描述',
    create_time DATETIME COMMENT '注册时间',
    status INT DEFAULT 1 COMMENT '角色状态 1: 激活 0: 禁用'
);

# 用户角色关系表
CREATE TABLE user_role_relation(
    id BIGINT PRIMARY KEY auto_increment,
    user_id BIGINT,
    role_id BIGINT
);

# 菜单表
CREATE TABLE menu(
    id BIGINT PRIMARY KEY auto_increment,
    parent_id BIGINT COMMENT '父级ID',
    create_time DATETIME COMMENT '创建时间',
    name VARCHAR(32) UNIQUE COMMENT '菜单名称',
    level INT	COMMENT '菜单级数',
    sort INT	COMMENT '菜单排序',
    icon VARCHAR(200) COMMENT '菜单前端图标',
    hidden INT COMMENT '控制前端是否显示 1:显示 0: 隐藏'
);

# 角色菜单表
CREATE TABLE role_menu_relation(
    id BIGINT PRIMARY KEY auto_increment,
    menu_id BIGINT,
    role_id BIGINT
);

# 资源表
CREATE TABLE resource(
    id BIGINT PRIMARY KEY auto_increment,
    category_id BIGINT COMMENT '分类ID',
    create_time DATETIME COMMENT '创建时间',
    name VARCHAR(32) UNIQUE COMMENT '资源名称',
    url VARCHAR(200) COMMENT '资源URL',
    description LONGTEXT COMMENT '描述'
);

# 资源分类表
CREATE TABLE resource_category(
    id BIGINT PRIMARY KEY auto_increment,
    name VARCHAR(32) UNIQUE COMMENT '分类名称',
    create_time DATETIME COMMENT '创建时间',
    sort INT COMMENT '排序',
    description LONGTEXT COMMENT '描述'
);

# 角色资源表
CREATE TABLE role_resource_relation(
    id BIGINT PRIMARY KEY auto_increment,
    resource_id BIGINT,
    role_id BIGINT
);