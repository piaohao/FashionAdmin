export default [
  // user
  {
    path: '/user',
    component: '../layouts/UserLayout',
    routes: [
      {path: '/user', redirect: '/user/login'},
      {path: '/user/login', component: './User/Login'},
      {path: '/user/register', component: './User/Register'},
      {path: '/user/register-result', component: './User/RegisterResult'},
    ],
  },
  {
    path: '/',
    component: '../layouts/BasicLayout',
    Routes: ['src/pages/Authorized'],
    authority: ['admin', 'user'],
    routes: [
      // dashboard
      {path: '/', redirect: '/system/user'},
      {
        path: "/system",
        name: "系统管理",
        icon: "system",
        routes: [
          {
            path: "/system/user",
            name: "用户管理",
            icon: "user",
            component: "./System/User",
          },
          {
            "path": "/system/role",
            "name": "角色管理",
            "icon": "role",
            "component": "./System/Role",
          },
          {
            "path": "/system/permission",
            "name": "权限管理",
            "icon": "permission",
            "component": "./System/Permission",
          }
        ],
        "code": "system",
        "parentCode": "root"
      },
      {
        "path": "/test",
        "name": "测试管理",
        "icon": "test",
        "routes": [
          {
            "path": "/test/index",
            "name": "测试首页",
            "icon": "test",
            "component": "./Test/Test",
          }
        ],
      }
    ]
  },
];
