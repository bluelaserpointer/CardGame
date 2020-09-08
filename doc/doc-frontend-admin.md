# **运维端需求**：

## 介绍

运维端作为管理全局项目数据包括基本游戏数据处理与运行日志查看的平台，因此需要做到能够按照时段观测数据，并且能将可控领域细分。

## 功能需求	

+ 满足运维端的功能需求
  + 游戏性数据的增删改查
  + 关卡布局设计、关卡报酬设计
  + 游戏玩家持有道具、卡片具体数据修改
  
  
  
+ 拓展性高的 DashBoard，定时生成并绘制日志图表以进行运维管理（崩溃日志数量、登录人数等）

  ![frontend-1](B:\Edward\Study\GitHub\CardGame\doc\frontend-1.png)

![frontend-3](B:\Edward\Study\GitHub\CardGame\doc\frontend-3.png)

## 技术栈

+ VueJs
+ 测试
  + Cypress
  + Jest

## 测试

+ 单元测试：出于运维端平台特性的考虑，其单元测试主要针对以下三个方面进行测试

1. 为包装后的 axios 进行请求结果测试作为主要目的
2. Mock数据作为请求返回的评判标准
3. 以模块为单位判定其发送请求次数（请求之间进行嵌套的场景）

+ E2E测试：作为网页端的运维前端，其主要操作仅限于键盘输入与鼠标点击等基本操作，因此主要通过其运维的业务逻辑作为流程，来判断每个流程分支中，画面渲染是否正确。

  ![frontend-2](B:\Edward\Study\GitHub\CardGame\doc\frontend-2.png)

## 访问权限

管理员权限下，支持以下功能访问

基本API：

+ /List：通过Pagination控制数据获取，并返回相应分表操作

+ /addName：添加 *Name*实体类 的实例

+ /updateName：更新 *Name*实体类 的实例

+ /deleteName：删除 *Name*实体类 的实例

+ /confirmDelete：当前运维端使用用户的确认请求操作

  ![frontend-6](B:\Edward\Study\GitHub\CardGame\doc\frontend-6.png)

游戏关卡设计API：

+ /updateChapterPhaseStrategy：增添/修改 *ChapterX-PhaseX* 中敌人布局信息

管理类别：运维端目前设置了 "Admin" 一种管理类别，该运维前端框架可后续添加其他身份的管理员，通过动态增添路由的方式设置权限，**并在后端相应设置权限后将能实现多身份运维**，例如：关卡编辑员、数据编辑员、高层管理员等

![frontend-5](B:\Edward\Study\GitHub\CardGame\doc\frontend-5.png)