import { getMenu } from '@/services/menu';

export default {
  namespace: 'menu',

  state: [],

  effects: {
    *fetch(_, { call, put }) {
      const response = yield call(getMenu);
      yield put({
        type: 'save',
        payload: response,
      });
    },
  },

  reducers: {
    save(state, action) {
      return [...action.payload];
    },
  },
};
