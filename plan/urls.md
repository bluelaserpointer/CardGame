## url 总览

- 登录请求

  url: /exsample/login

  post: [...] （这里格式随便，参考 JSON 吧）

- 登出请求

- 邮箱获取

- 邮件获取

- 世界地图获取（学校）

- 关卡表面信息获取（关卡封面，说明等）

- 进入关卡请求

- 退出关卡请求（客户端发送战斗结果）

- 运维修改请求：

  - 可修改实体集 base url：

    - 游戏内活动：/activity
    - 管理员信息： /admin
    - 卡片信息： /card
    - 关卡信息： /chapter
    - 道具信息： /item
    - 邮件信息： /mail
    - 玩家持有卡片相关信息： /ownCard
    - 玩家持有道具相关信息： /ownItem
    - 用户信息： /user

  - 更新操作：

    Update: /update{var1}{var2}{var3}...

  - 添加操作：

    Add: /add{var1}{var2}{var3}...

  - 删除操作：

    Delete: /delete{Id}

  - 删除所有：

    Delete: /delete/all

  - 获取操作：

    Get: /get{Id}

  - 获取所有：

    Get: /get/all

  部分实体集可能有部分操作缺失。
