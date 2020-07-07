## Naomi Progress Log

### 第一周 负责后端项目（目前）

### 第二周 待定

### 第三周 待定

### 第四周 待定

---

### 7 月 7 日

1. 大家一起讨论了数据库的大体设计，生成了最初的草图,将数据大致划分成如下实体类：（详情见 ppt 第七页）
2. 最终在实现的时候的实体类（详情见 ppt 第八页）
   后端用 spring 作为开发工具，由于目前还没有 Amazon 服务器，因此先是创建了不支持 Amazon 服务器的后端项目，计划在获得 Amazon 服务器后再将项目移植过去。
3. 在设计好实体类，我们对前后端传输的 API 逻辑进行确定，确定后确定下来的逻辑：
   - parent link: /{entityName}
   - Update: /update{var1}{var2}{var3}...
   - Add: /add{var1}{var2}{var3}...
   - Delete: /delete{Id}
   - Delete: /delete/all
   - Get: /get{Id}
   - Get: /get/all

### 目前进展： 后端实体类与 repository 已经完成，准备完善 API 的部分。

### 明天计划：

- 实现后端，且能与运维前端进行数据传输。
- 将游戏目前已有的数据存入后端。
- 在上一步完成后开始调试游戏的数据传输功能。
