import request from '@/utils/request';

export async function getMenu() {
  return request('/api/getMenu');
}
