import request from '@/utils/request';

export async function list(params) {
  return request('/api/permissions', {
    method: "POST",
    body: {
      ...params,
    }
  });
}

export async function treeData(params) {
  return request('/api/permission/tree', {
    method: "POST",
    body: {
      ...params,
    }
  });
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
