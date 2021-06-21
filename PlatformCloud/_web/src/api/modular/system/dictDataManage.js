import { axios } from '@/utils/request'

/**
 * 查询系统字典值
 *
 * @author yubaoshan
 * @date 2020/5/17 02:24
 */
export function sysDictDataPage (parameter) {
  return axios({
    url: '/sysDictData/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 检索系统字典值
 *
 * @author 李彪
 * @date 2021/04/16
 */
export function sysDictDataSearch (parameter) {
  return axios({
    url: '/sysDictData/search',
    method: 'get',
    params: parameter
  })
}
/**
 * 查询系统某个字典类型下的所有字典值
 *
 * @author 李彪
 * @date 2021/4/12
 */
export function sysDictDataFindByCode (parameter) {
  return axios({
    url: '/sysDictData/findByCode',
    method: 'get',
    params: parameter
  })
}
/**
 * 根据字典值父级查询子级字典值
 *
 * @author libiao
 * @date 2021/4/12
 */
export function sysDictDataChildren (parameter) {
  return axios({
    url: '/sysDictData/children/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 根据字典Code查询下级字典值，树状
 *
 * @author libiao
 * @date 2021/5/21
 */
export function getSysDictDataTreeByCode (parameter) {
  return axios({
    url: '/sysDictData/treeByCode',
    method: 'get',
    params: parameter
  })
}

/**
 * 根据字典类型查询一级字典值
 *
 * @author libiao
 * @date 2021/5/21
 */
export function getSysDictDataByParentIdIsNull (parameter) {
  return axios({
    url: '/sysDictData/listByParentIdIsNull',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加系统字典值
 *
 * @author yubaoshan
 * @date 2020/5/17 02:24
 */
export function sysDictDataAdd (parameter) {
  return axios({
    url: '/sysDictData/add',
    method: 'post',
    data: parameter
  })
}

/**
 * 编辑系统字典值
 *
 * @author yubaoshan
 * @date 2020/5/17 02:25
 */
export function sysDictDataEdit (parameter) {
  return axios({
    url: '/sysDictData/edit',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除系统字典值
 *
 * @author yubaoshan
 * @date 2020/5/17 02:25
 */
export function sysDictDataDelete (parameter) {
  return axios({
    url: '/sysDictData/delete',
    method: 'post',
    data: parameter
  })
}
