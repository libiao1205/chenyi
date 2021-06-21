import { axios } from '@/utils/request'

/**
 * 查询元数据表
 *
 * @author libiao
 * @date 2021/04/14
 */
export function metadataColumnPage (parameter) {
  return axios({
    url: '/metadata/metadataColumn/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 检索元数据字段
 *
 * @author libiao
 * @date 2021/04/14
 */
export function metadataColumnSearchPage (parameter) {
  return axios({
    url: '/metadata/metadataColumn/searchPage',
    method: 'get',
    params: parameter
  })
}

/**
 * 查询元数据字段
 *
 * @author libiao
 * @date 2021/04/14
 */
export function metadataColumnChildrenList (parameter) {
  return axios({
    url: '/metadata/metadataColumn/children/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加元数据字段
 *
 * @author libiao
 * @date 2021/04/14
 */
export function metadataColumnAdd (parameter) {
  return axios({
    url: '/metadata/metadataColumn/add',
    method: 'post',
    data: parameter
  })
}
/**
 * 更新元数据字段
 *
 * @author libiao
 * @date 2021/04/14
 */
export function metadataColumnEdit (parameter) {
  return axios({
    url: '/metadata/metadataColumn/edit',
    method: 'post',
    data: parameter
  })
}
/**
 * 删除元数据字段
 *
 * @author libiao
 * @date 2021/04/14
 */
export function metadataColumnDelete (parameter) {
  return axios({
    url: '/metadata/metadataColumn/delete',
    method: 'post',
    data: parameter
  })
}
/**
 * 更新元数据字段
 *
 * @author libiao
 * @date 2021/04/14
 */
export function metadataColumnFieldConstraint (parameter) {
  return axios({
    url: '/metadata/metadataColumn/fieldConstraint',
    method: 'post',
    data: parameter
  })
}
/**
 * 查询元数据字段约束列表
 *
 * @author libiao
 * @date 2021/05/21
 */
export function metadataColumnConstraintList (parameter) {
  return axios({
    url: '/metadata/metadataColumn/fieldConstraintList',
    method: 'get',
    params: parameter
  })
}
