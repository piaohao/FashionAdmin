export default [
  {
    path: '/user',
    routes: [
      { redirect: '/user/login', path: '/user' },
      {
        path: '/user/login',
        component: './User/Login',
      },
      { path: '/user/register', component: './User/Register' },
    ],
    component: '../layouts/UserLayout',
  },
  {
    path: '/',
    routes: [
      { redirect: '/dashboard/welcome', path: '/' },
      {
        path: '/dashboard/welcome',
        component: './Dashboard/Welcome',
        name: '首页',
        icon: 'home',
      },
      {
        path: '/system',
        routes: [
          {
            path: '/system/user',
            routes: [],
            component: './System/User',
            code: 'system_user',
            parentCode: 'system',
            name: '用户管理',
            icon: 'user',
          },
          {
            path: '/system/role',
            routes: [],
            component: './System/Role',
            code: 'system_role',
            parentCode: 'system',
            name: '角色管理',
            icon: 'role',
          },
          {
            path: '/system/permission',
            routes: [],
            component: './System/Permission',
            code: 'system_permission',
            parentCode: 'system',
            name: '权限管理',
            icon: 'permission',
          },
        ],
        code: 'system',
        parentCode: 'root',
        name: '系统管理',
        icon: 'appstore',
      },
      {
        path: '/test',
        routes: [
          {
            path: '/test/index',
            routes: [],
            component: './Test/Test',
            code: 'test_index',
            parentCode: 'test',
            name: '测试首页',
            icon: 'test',
          },
        ],
        code: 'test',
        parentCode: 'root',
        name: '测试管理',
        icon: 'experiment',
      },
      { component: '404' },
    ],
    component: '../layouts/BasicLayout',
  },
];
