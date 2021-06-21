import { axios } from '@/utils/request'

/**
 * 查询数据集
 *
 * @author libiao
 * @date 2021/04/15
 */
export function getDataCollectPage (parameter) {
  return axios({
    url: '/dataCollect/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 查询数据集，不分页
 *
 * @author libiao
 * @date 2021/04/26
 */
export function getDataCollectList () {
  return axios({
    url: '/dataCollect/list',
    method: 'get'
  })
}

/**
 * 查询某数据集下的所有元数据表
 *
 * @author libiao
 * @date 2021/04/15
 */
export function getFindByCodeMetadataTable (parameter) {
  return axios({
    url: '/dataCollect/findByCodeMetadataTable',
    method: 'get',
    params: parameter
  })
}
/**
 * 添加数据集
 *
 * @author libiao
 * @date 2021/04/15
 */
export function dataCollectAdd (parameter) {
  return axios({
    url: '/dataCollect/add',
    method: 'post',
    data: parameter
  })
}
/**
 * 更新数据集
 *
 * @author libiao
 * @date 2021/04/15
 */
export function dataCollectEdit (parameter) {
  return axios({
    url: '/dataCollect/edit',
    method: 'post',
    data: parameter
  })
}
/**
 * 删除数据集
 *
 * @author libiao
 * @date 2021/04/15
 */
export function dataCollectDelete (parameter) {
  return axios({
    url: '/dataCollect/delete',
    method: 'post',
    data: parameter
  })
}
