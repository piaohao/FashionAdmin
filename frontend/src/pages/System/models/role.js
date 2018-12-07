import {query as queryRoles, saveRole, removeRole, saveRolePermission, getRolePermission} from '@/services/role';
import {list} from '@/services/permission';
import {routerRedux} from "dva/router"

export default {
  namespace: 'role',

  state: {
    data: {
      list: [],
      pagination: {},
    },
    allPermissions: [],
    permissionIds: [],
  },

  effects: {
    * fetch({payload}, {call, put}) {
      const response = yield call(queryRoles, payload);
      yield put({
        type: 'save',
        payload: response,
      });
    },
    * saveRole({payload, callback}, {call, put}) {
      yield call(saveRole, payload);
      yield put(routerRedux.push("/system/role", {...payload}));
      if (callback) callback();
    },
    * remove({payload, callback}, {call, put}) {
      const {selectedRows} = payload;
      yield call(removeRole, selectedRows);
      yield put(routerRedux.push("/system/role", {...payload}));
      if (callback) callback();
    },
    * update({payload, callback}, {call, put}) {
      yield call(updateRule, payload);
      yield put(routerRedux.push("/system/role", {...payload}));
      if (callback) callback();
    },
    * fetchAllPermissions({payload}, {call, put}) {
      const response = yield call(list, payload);
      yield put({
        type: 'savePermissions',
        payload: response,
      });
    },
    * saveRolePermission({payload, callback}, {call, put}) {
      yield call(saveRolePermission, payload);
      yield put(routerRedux.push("/system/role", {...payload}));
      if (callback) callback();
    },
    * getRolePermission({payload, callback}, {call, put}) {
      const response = yield call(getRolePermission, payload);
      yield put({
        type: 'savePermissionIds',
        payload: response,
      });
      if (callback) callback();
    },
  },

  reducers: {
    save(state, action) {
      return {
        ...state,
        data: action.payload,
      };
    },
    savePermissions(state, action) {
      return {
        ...state,
        allPermissions: action.payload,
      };
    },
    savePermissionIds(state, action) {
      return {
        ...state,
        permissionIds: action.payload,
      };
    },
  },

  subscriptions: {
    /**
     * 监听浏览器地址，当跳转到 /user 时进入该方法
     * @param dispatch 触发器，用于触发 effects 中的 query 方法
     * @param history 浏览器历史记录，主要用到它的 location 属性以获取地址栏地址
     */
    setup({dispatch, history}) {
      history.listen((location) => {
        if (location.pathname === "/system/role" && location.state && Object.keys(location.state).length > 0) {
          // 调用 effects 属性中的 query 方法，并将 location.state 作为参数传递
          dispatch({
            type: 'role/fetch',
            payload: {...location.state},
          })
        }
      });
    },
  },
};
