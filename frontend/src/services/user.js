import request from '@/utils/request';

export async function query(params) {
  return request('/api/users', {
    method: "POST",
    body: {
      ...params,
    }
  });
}

export async function queryCurrent() {
  return request('/api/currentUser');
}

export async function saveUser(params) {
  return request('/api/saveUser', {
    method: "POST",
    body: {
      ...params,
    }
  });
}

export async function removeUser(params) {
  return request('/api/removeUser', {
    method: "POST",
    body: {
      data: params,
    }
  });
}
