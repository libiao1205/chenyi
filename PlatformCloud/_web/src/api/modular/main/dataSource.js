import { axios } from '@/utils/request'

/**
 * 查询数据源
 *
 * @author libiao
 * @date 2021/04/25
 */
export function getDataSourcePage (parameter) {
  return axios({
    url: '/dataSource/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 查询数据源字段
 *
 * @author libiao
 * @date 2021/04/25
 */
export function getFindColumnByCode (parameter) {
  return axios({
    url: '/dataSource/findColumnByCode',
    method: 'get',
    params: parameter
  })
}

/**
 * 查询数据源文件
 *
 * @author libiao
 * @date 2021/04/29
 */
export function getFile (parameter) {
  return axios({
    url: '/dataSource/getFile',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加数据源
 *
 * @author libiao
 * @date 2021/04/25
 */
export function dataSourceAdd (parameter) {
  return axios({
    url: '/dataSource/add',
    method: 'post',
    data: parameter
  })
}
/**
 * 更新数据源
 *
 * @author libiao
 * @date 2021/04/25
 */
export function dataSourceEdit (parameter) {
  return axios({
    url: '/dataSource/edit',
    method: 'post',
    data: parameter
  })
}
/**
 * 删除数据源
 *
 * @author libiao
 * @date 2021/04/25
 */
export function dataSourceDelete (parameter) {
  return axios({
    url: '/dataSource/delete',
    method: 'post',
    data: parameter
  })
}
/**
 * 执行抽取
 *
 * @author libiao
 * @date 2021/04/27
 */
export function dataSourceExecExtract (parameter) {
  window.location.href = process.env.VUE_APP_API_BASE_URL + '/main/dataSource/execExtract' + parameter
}

/**
 * 测试连接
 *
 * @author libiao
 * @date 2021/05/07
 */
  export function dataSourceTestConnect (parameter) {
  return axios({
    url: '/dataSource/testConnect',
    method: 'post',
    params: parameter
  })
}
