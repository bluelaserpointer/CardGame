Edward Raymond He 518030990022

### 2020.07.07

#### Finished: 

1. 根据项目需求将第三方组件的小型运维面板进行适配
2. 通过简单数据进行了测试并成功渲染
3. 增加了以下后端实体类的相应 增&改 的 Axios 请求
   (Card, Item, Player, Activity, Mail)

#### Currently working on:

1. 将前后端请求进行结合(部分实体类完整的增删改查)
2. 前后端的登录认证

### 2020.07.08

#### Finished: 

1. 增加了 Activity，Mail 的编辑面板
2. 增加了游戏关卡设计模块
3. 增加了其他实体集编辑面板的删除功能(包括从安全性出发的部分)

#### Currently working on:

1. 前后端请求发送与接受的交互环节测试

### 2020.07.09

#### Finished: 

1. 完善了更多的基本实体集的增删改查功能
2. 在测试前后端交互阶段时完善了后端的部分bug (与非结构化数据相关的 repository class)
3. 完善了 Activity和Mail (游戏活动与游戏邮件) 的发布与展示，OwnItem和OwnCard的展示

#### Currently working on:

1. Activity、Mail、OwnCard、OwnItem 的更新与删除
2. 游戏关卡设计模块更人性化的可视化 (目前能从数据库变化与 console.log 等观察到具体的数据，虽然是正确的，但对后期复杂的关卡维护更新等不友好)
3. 登录登出时的数据库校验 (目前前端安全性已提升，待与后端进行进一步交互)

### 2020.07.10

#### Finished: 

1. 游戏关卡设计模块可视化及完善与相应后端的更改调试
2. 实体集表格管理中的搜索功能
3. 登录登出相关的校验与相应后端的更改调试

#### Currently working on:

1. Activity、Mail、OwnCard、OwnItem 的更新与删除
2. Android Studio的安装和Gradle问题，准备加入手游UI的布局工作

### 2020.07.13

#### Finished: 

1. 进行了Android Studio的UI布局学习
2. 设计了好友列表及添加好友相关的UI界面与页面跳转逻辑
3. 进行了游戏机制的进一步讨论与更改

### 2020.07.14

#### Finished: 

1. 设计了战斗结算画面的两个UI，并编写了相应渲染逻辑
2. 为Client端的登录进行了与后端的衔接和验证 (主要在调试并学习Android Studio自带的部分UI逻辑跳转)
3. 后端对User类进行了相应数据结构更新，并在Client端的主界面进行了数据衔接

### 2020.07.15

#### Finished: 

1. 由于游戏机制的更改，又新增了其他与游戏关卡奖励相关的Java类代码
2. 在新Branch上进行了单元测试相关初始测试等（较基础）

#### Currently working on:

1. 对目前在运维端的部分进行单元测试

### 2020.07.16

#### Finished: 

1. 针对了 Card, Item 实体类的操作面板进行了基本的单元测试
2. 针对后端关卡设计环节 Chapter, ChapterPhase 的奖励设置进行了调试

#### Currently working on:

1. 正在编写更多的单元测试代码
2. Player相关的操作模板需要针对后端改动进行跟进之后再做相关单元测试代码