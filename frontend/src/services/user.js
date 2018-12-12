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

export async function getRoleIds(params) {
  return request('/api/getRoleIds', {
    method: "POST",
    body: {
      ...params,
    }
  });
}

export async function saveUserRole(params) {
  return request('/api/saveUserRole', {
    method: "POST",
    body: {
      ...params,
    }
  });
}
