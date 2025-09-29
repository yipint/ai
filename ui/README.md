# IMIS 管理系统前端

基于 Vue3 + Element Plus 的用户管理系统前端界面。

## 技术栈

- Vue 3.3.4
- Element Plus 2.3.8
- Vue Router 4.2.4
- Pinia 2.1.6
- Axios 1.4.0
- Vite 4.4.5

## 功能特性

- 用户列表展示（分页、搜索）
- 用户新增、编辑、删除
- 响应式布局
- 表单验证
- 接口错误处理

## 开发环境

### 安装依赖
```bash
cd ui
npm install
```

### 启动开发服务器
```bash
npm run dev
```

访问 http://localhost:3000

### 构建生产版本
```bash
npm run build
```

## 项目结构

```
ui/
├── src/
│   ├── api/           # API 接口
│   │   ├── request.js # axios 配置
│   │   └── user.js    # 用户相关接口
│   ├── router/        # 路由配置
│   │   └── index.js
│   ├── views/         # 页面组件
│   │   └── UserList.vue
│   ├── App.vue        # 根组件
│   └── main.js        # 入口文件
├── index.html
├── package.json
├── vite.config.js
└── README.md
```

## API 接口

后端接口地址：http://localhost:8080

- GET /api/user/page - 分页查询用户
- GET /api/user/{id} - 根据ID查询用户
- POST /api/user - 新增用户
- PUT /api/user - 修改用户
- DELETE /api/user/{id} - 删除用户

## 注意事项

1. 确保后端服务已启动（端口 8080）
2. 前端开发服务器运行在端口 3000
3. 已配置代理，API 请求会自动转发到后端
