import request from './request'

export const userApi = {
  // 分页查询用户
  pageUsers(params) {
    return request.get('/api/user/page', { params })
  },
  
  // 根据ID查询用户
  getUserById(id) {
    return request.get(`/api/user/${id}`)
  },
  
  // 新增用户
  createUser(data) {
    return request.post('/api/user', data)
  },
  
  // 修改用户
  updateUser(data) {
    return request.put('/api/user', data)
  },
  
  // 删除用户
  deleteUser(id) {
    return request.delete(`/api/user/${id}`)
  }
}
