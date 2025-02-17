import request from '@/utils/request'

// 查询行程列表
export function listTrips(query) {
  return request({
    url: '/sc2/trips/list',
    method: 'get',
    params: query
  })
}

// 查询行程详细
export function getTrips(tripId) {
  return request({
    url: '/sc2/trips/' + tripId,
    method: 'get'
  })
}

// 新增行程
export function addTrips(data) {
  return request({
    url: '/sc2/trips',
    method: 'post',
    data: data
  })
}

// 修改行程
export function updateTrips(data) {
  return request({
    url: '/sc2/trips',
    method: 'put',
    data: data
  })
}

// 删除行程
export function delTrips(tripId) {
  return request({
    url: '/sc2/trips/' + tripId,
    method: 'delete'
  })
}
