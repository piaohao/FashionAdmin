import {query as queryUsers, saveUser, removeUser, getRoleIds,saveUserRole} from '@/services/user';
import {allRoles} from "@/services/role";
import {routerRedux} from "dva/router"

export default {
  namespace: 'sysUser',

  state: {
    data: {
      list: [],
      pagination: {},
    },
    roleIds: [],
    allRoles: [],
  },

  effects: {
    * fetch({payload}, {call, put}) {
      const response = yield call(queryUsers, payload);
      yield put({
        type: 'save',
        payload: response,
      });
    },
    * saveUser({payload, callback}, {call, put}) {
      yield call(saveUser, payload);
      yield put(routerRedux.push("/system/user", {...payload}));
      if (callback) callback();
    },
    * remove({payload, callback}, {call, put}) {
      const {selectedRows} = payload;
      yield call(removeUser, selectedRows);
      yield put(routerRedux.push("/system/user", {...payload}));
      if (callback) callback();
    },
    * update({payload, callback}, {call, put}) {
      yield call(updateRule, payload);
      yield put(routerRedux.push("/system/user", {...payload}));
      if (callback) callback();
    },
    * getRoleIds({payload}, {call, put}) {
      const response = yield call(getRoleIds, payload);
      yield put({
        type: 'saveRoleIds',
        payload: response,
      });
    },
    * allRoles({payload}, {call, put}) {
      const response = yield call(allRoles, payload);
      yield put({
        type: 'saveAllRoles',
        payload: response,
      });
    },
    * saveUserRole({payload, callback}, {call, put}) {
      yield call(saveUserRole, payload);
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
    saveRoleIds(state, action) {
      return {
        ...state,
        roleIds: action.payload,
      };
    },
    saveAllRoles(state, action) {
      return {
        ...state,
        allRoles: action.payload,
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
        if (location.pathname === "/system/user" && location.state && Object.keys(location.state).length > 0) {
          // 调用 effects 属性中的 query 方法，并将 location.state 作为参数传递
          dispatch({
            type: 'sysUser/fetch',
            payload: {...location.state},
          })
        }
      });
    },
  },
};
