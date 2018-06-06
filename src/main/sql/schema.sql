--创建数据库
CREATE DATABASE seckill;

--使用数据库
USE seckill;

--创建秒杀库存表
create table seckill (
	seckill_id bigint not null auto_increment comment '商品库存id',
    name varchar(120) not null comment '商品名称',
    number int not null comment '库存数量',
    start_time timestamp not null comment '秒杀开始时间',
    end_time timestamp not null comment '秒杀结束时间',
    create_time timestamp not null default current_timestamp comment '创建时间',
    PRIMARY KEY (seckill_id),
    key idx_start_time(start_time),
    key inx_end_time(end_time),
    key inx_create_time(create_time)
)engine=InnoDB auto_increment=1000 default charset=utf8 comment='秒杀库存表';


--数据初始化
insert into
	seckill(name, number, start_time, end_time)
values
	('1000元秒杀iphone6', 100, '2018-04-20 00:00:00', '2018-04-30 00:00:00'),
    ('2000元秒杀iphone7', 100, '2018-04-20 00:00:00', '2018-04-30 00:00:00'),
    ('3000元秒杀iphone8', 100, '2018-04-20 00:00:00', '2018-04-30 00:00:00'),
    ('500元秒杀ipad', 100, '2018-04-20 00:00:00', '2018-04-30 00:00:00'),
    ('500元秒杀华为手机', 100, '2018-04-20 00:00:00', '2018-04-30 00:00:00'),
    ('500元秒杀小米手机', 100, '2018-04-20 00:00:00', '2018-04-30 00:00:00'),
    ('500元秒杀三星手机', 100, '2018-04-20 00:00:00', '2018-04-30 00:00:00'),
    ('500元秒杀魅族手机', 100, '2018-04-20 00:00:00', '2018-04-30 00:00:00');



-秒杀成功明细表
 create table success_killed (
	seckill_id bigint not null comment '秒杀商品id',
    user_phone bigint not null comment '用户手机号',
    state tinyint not null default -1 comment '-1：无效 0：成功 1：已付款 2：已发货',
    create_time timestamp not null default current_timestamp comment '创建时间',
    primary key(seckill_id, user_phone),
    key idx_create_time(create_time)
 )engine=InnoDB default charset=utf8 comment '秒杀成功明细表'