import request from '@/utils/request';

export async function query(params) {
  return request('/api/roles', {
    method: "POST",
    body: {
      ...params,
    }
  });
}

export async function saveRole(params) {
  return request('/api/saveRole', {
    method: "POST",
    body: {
      ...params,
    }
  });
}

export async function saveRolePermission(params) {
  return request('/api/saveRolePermission', {
    method: "POST",
    body: {
      ...params,
    }
  });
}

export async function getRolePermission(params) {
  return request('/api/getRolePermission', {
    method: "POST",
    body: {
      ...params,
    }
  });
}

export async function removeRole(params) {
  return request('/api/removeRole', {
    method: "POST",
    body: {
      data: params,
    }
  });
}
