import { axios } from '@/utils/request'

/**
 * 查询数据采集项
 * @author libiao
 * @date 2021/04/22
 */
export function getDataGatherPage (parameter) {
  return axios({
    url: '/dataGather/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加数据采集项
 *
 * @author libiao
 * @date 2021/04/22
 */
export function dataGatherAdd (parameter) {
  return axios({
    url: '/dataGather/add',
    method: 'post',
    data: parameter
  })
}
/**
 * 更新数据采集项
 *
 * @author libiao
 * @date 2021/04/22
 */
export function dataGatherUpdateGatherStatus (parameter) {
  return axios({
    url: '/dataGather/updateGatherStatus',
    method: 'post',
    data: parameter
  })
}
/**
 * 删除数据采集项
 *
 * @author libiao
 * @date 2021/04/22
 */
export function dataGatherDelete (parameter) {
  return axios({
    url: '/dataGather/delete',
    method: 'post',
    data: parameter
  })
}

/**
 * 查询数据采集项
 * @author libiao
 * @date 2021/04/22
 */
export function dataGatherExec (parameter) {
  return axios({
    url: '/dataGather/exec',
    method: 'get',
    params: parameter
  })
}

/**
 * 获取采集日志
 * @author libiao
 * @date 2021/05/17
 */
export function getGatherLog (parameter) {
  return axios({
    url: '/dataGather/getGatherLog',
    method: 'get',
    params: parameter
  })
}
/**
 * 下载采集内容
 *
 * @author libiao
 * @date 2021/04/23
 */
export function downGatherConetnt (parameter) {
  window.location.href = process.env.VUE_APP_API_BASE_URL + '/main/dataGather/downFolder' + parameter
}
