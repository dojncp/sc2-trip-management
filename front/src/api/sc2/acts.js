import request from '@/utils/request'

// 查询移动列表
export function listActs(query) {
  return request({
    url: '/sc2/acts/list',
    method: 'get',
    params: query
  })
}

// 查询移动详细
export function getActs(actId) {
  return request({
    url: '/sc2/acts/' + actId,
    method: 'get'
  })
}

// 新增移动
export function addActs(data) {
  return request({
    url: '/sc2/acts',
    method: 'post',
    data: data
  })
}

// 修改移动
export function updateActs(data) {
  return request({
    url: '/sc2/acts',
    method: 'put',
    data: data
  })
}

// 删除移动
export function delActs(actId) {
  return request({
    url: '/sc2/acts/' + actId,
    method: 'delete'
  })
}

// 获取指定trip下的acts
export function getActsOfTheTrip(tripName) {
  return request({
    url: 'sc2/acts/acts-of-trip',
    method: 'get',
    params: {
      tripName: tripName
    }
  })
}

