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

### 2020.07.17

#### Finished: 

1. 针对 Card, Item, OwnCard, OwnItem 做了相对较全面的单元测试（排除了 Element-UI 涵盖的基本表格渲染函数）
2. 针对 OwnCard, OwnItem 添加了相应增删改操作（在进行游戏机制讨论后决定允许修改玩家所持游戏数据，原本仅涵盖了查看）
3. 整理并完善了前端代码风格和 axios请求函数判断分支接口
4. 调试了后端 OwnCard 相关存入更新等操作的Bug

#### Currently working on:

1. 正在编写更多的单元测试代码
2. 正在学习 e2e 相关内容

### 2020.07.20

#### Finished: 

1. 调试了后端Chapter相关的Bug
2. 完成了前端运维的关卡奖励设置面板 (暂时未做单元测试)

#### Currently working on:

1. 正在编写更多的单元测试代码
2. 正在学习 e2e 相关内容

### 2020.07.21

#### Finished: 

1. 添加了后端有关玩家队伍设置的实体类和相应Java文件
2. 完成了 Card、Item 的e2e测试
3. 修复了前端所有表单验证Bug

#### Currently working on:

1. 将完成更多e2e测试

### 2020.07.22

#### Finished: 

1. 完成了 Phase, Player, OwnItem, OwnCard, Activity, Mail 的e2e测试
2. 修复了前端测试所需的 className

#### Currently working on:

1. 正在做更多与Phase相关的测试（先前遗漏了些）
2. 正在学习认证授权相关的衔接内容

### 2020.07.23

#### Finished: 

1. 了解了 Github Action，CI 在项目上的应用并进行了基础的实施，将在后续进行更完善的测试改动后 commit 前端的 yml 文件
2. 了解了前端控制面板自带的验证授权流程并进行了相应测试

#### Currently working on:

1. 正在尝试更替前端的基础请求（使用包装过的Axios），并完成前后端交接（在更换了包装后的请求函数后，非简单请求会出现Bug，所以在调试中）

### 2020.07.24

#### Finished: 

1. 制作了Mission相关前端页面和控制面板
2. 已经学会了当前运维前端的请求环节（由于是采用他人的控制面板模板，因此从Mock-Server转换成本地请求发送花了近两天时间，现在已成功完成初步的请求发送，待后端将Admin合并入User后进行下一环节）
3. 讨论了下一周的开发内容（游戏内容取舍、开发步骤等）

#### Currently working on:

1. 在调试于 2020.07.21 修复的认证Bug 所引发的单元测试相关Bug（与 element-ui相关）

### 2020.07.27

#### Finished: 

1. 根据后端替换了目前所有请求数据格式
2. 完善了运维前端目前的登陆验证和请求发送权限（设置Token，使用封装Axios）
3. 调试了后端接口的一些Bug
4. 解决了除了 "封装 axios interceptors .then() .catch()操作" 以外的单元测试Bug

#### Currently working on:

1. 由于调试过程已经超了两天，所以单元测试中封装 axios 的bug目前先暂停
2. e2e测试的 code coverage 网址中未覆盖 .vue文件

### 2020.07.28

#### Finished: 

1. 修改了 OwnCard 在前端的控制面板
2. 添加了在线人数统计、登入登出日志统计
3. 新增了基础实体类的数据生成SQL语句

#### Currently working on:

1. 在写更多实体类的数据生成SQL

### 2020.07.29

#### Finished: 

1. 主要在学习Aws部署前端还有本地环境搭建
2. 完善并测试了SQL数据

#### Currently working on:

1. 继续完善运维端的功能性需求
2. 继续调试运维端单元测试的异步操作Bug

### 2020.07.30

#### Finished: 

1. 布署了Aws前端运维，并成功连接后端服务器
2. 完成了Aws上Java和Maven的安装
3. 重新更正了MySQL
4. 调试单元测试Axios相关部分（仍然找不到解决方案，因此决定延后，并开发新功能）

#### Currently working on:

1. 完善更多的前端运维部分，如发送邮件等