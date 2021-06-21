import { axios } from '@/utils/request'

/**
 * 查询元数据表
 *
 * @author libiao
 * @date 2021/04/14
 */
export function metadataTablePage (parameter) {
  return axios({
    url: '/metadata/metadataTable/page',
    method: 'get',
    params: parameter
  })
}
/**
 * 查询所有元数据表，不分页
 *
 * @author libiao
 * @date 2021/04/15
 */
export function metadataTableList (parameter) {
  return axios({
    url: '/metadata/metadataTable/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 查询源数据表及字段，树状
 *
 * @author libiao
 * @date 2021/04/19
 */
export function metadataTableFindColumnTree (parameter) {
  return axios({
    url: '/metadata/metadataTable/findColumnTree',
    method: 'get',
    params: parameter
  })
}
/**
 * 添加元数据表
 *
 * @author libiao
 * @date 2021/04/14
 */
export function metadataTableAdd (parameter) {
  return axios({
    url: '/metadata/metadataTable/add',
    method: 'post',
    data: parameter
  })
}
/**
 * 更新元数据表
 *
 * @author libiao
 * @date 2021/04/14
 */
export function metadataTableEdit (parameter) {
  return axios({
    url: '/metadata/metadataTable/edit',
    method: 'post',
    data: parameter
  })
}
/**
 * 删除元数据表
 *
 * @author libiao
 * @date 2021/04/14
 */
export function metadataTableDelete (parameter) {
  return axios({
    url: '/metadata/metadataTable/delete',
    method: 'post',
    data: parameter
  })
}

/**
 * 导出excel
 *
 * @author libiao
 * @date 2021/04/20
 */
export function metadataTableExportExcel (parameter) {
  window.location.href = process.env.VUE_APP_API_BASE_URL + '/main/metadata/metadataTable/exportExcel' + parameter
}

/**
 * 下载导入模板
 *
 * @author libiao
 * @date 2021/04/21
 */
export function metadataTableDownloadTemplate (parameter) {
  window.location.href = process.env.VUE_APP_API_BASE_URL + '/main/metadata/metadataTable/downloadTemplate' + parameter
}
