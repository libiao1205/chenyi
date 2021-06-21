// eslint-disable-next-line no-unused-vars
function getDisplayName(dataList, value, defaultValue) {
    const data = dataList.find(c => c.code === value)
    if (data) { return data.value } else { return defaultValue }
}
// eslint-disable-next-line no-unused-vars
function getDisplayColumn(dataList, value, defaultValue) {
  const data = dataList.find(c => c.code === value)
  if (data) { return data.column } else { return defaultValue }
}

const state = {
  statusList: [ { value: '启用', code: 1 }, { value: '禁用', code: 0 } ],
  dataTypeList:
    [
      { value: '字符串', code: 1 },
      { value: '大文本', code: 2 },
      { value: '整数', code: 3 },
      { value: '长整数', code: 4 },
      { value: '浮点数', code: 5 },
      { value: '布尔值', code: 6 },
      { value: '日期', code: 7 },
      { value: 'json', code: 8 }
    ],
  metadataTypeList:
    [
      { code: 1, value: 'AS元数据' },
      { code: 2, value: 'MARC元数据' }
    ],
  dataSourceType:
    [
      { code: 'AS_DATASOURCE', value: 'ArchivesSpace系统', column: [{ code: 'ip', value: 'IP地址' }, { code: 'port', value: '端口' }, { code: 'username', value: '用户名' }, { code: 'password', value: '密码' }] },
      // { code: 'FEDORA_DATASOURCE', value: 'Fedora系统', column: [{ code: 'ip', value: 'IP地址' }, { code: 'port', value: '端口' }, { code: 'username', value: '用户名' }, { code: 'password', value: '密码' }] },
      { code: 'EXCEL_DATASOURCE', value: 'Excel数据文件', column: [{ code: 'excelFile', value: 'Excel文件' }] },
      { code: 'MARC_DATASOURCE', value: 'Marc数据文件', column: [{ code: 'marcFile', value: 'Marc文件' }] },
      { code: 'ES_DATASOURCE', value: '导出ES配置文件' }
    ],
  // es分词器
  esAnalyzerList: ['不分词', 'ngram_analyzer'],
  // 元数据字段约束code
  metadataFieldConstraint: 'metadata_field_constraint'

}

const getters = {
    getDataSource: (state) => (value) => {
        return getDisplayColumn(state.dataSourceType, value, {})
    },
    getDataSourceTypeName: (state) => (value) => {
      return getDisplayName(state.dataSourceType, value, '')
    },
  /*
  * 以下code前后端要一致
  * */
    getMarcDataSourceCode: (state) => () => {
      return 'MARC_DATASOURCE'
    },
    getAsDataSourceCode: (state) => () => {
      return 'AS_DATASOURCE'
    },
    getFedoraDataSourceCode: (state) => () => {
      return 'FEDORA_DATASOURCE'
    },
    getExcelDataSourceCode: (state) => () => {
      return 'EXCEL_DATASOURCE'
    },
    getExcelMetadataCode: (state) => () => {
      return 'EXCEL_MATADATA'
    },
    getMarcMetadataCode: (state) => () => {
      return 'MARC_MATADATA'
    },
    getASMetadataCode: (state) => () => {
      return 'AS_MATADATA'
    },
    getESMetadataCode: (state) => () => {
      return 'ES_MATADATA'
    },
    getMarcFileCode: (state) => () => {
      return 'excelFile'
    },
    getExcelFileCode: (state) => () => {
      return 'marcFile'
    }
}
export default {
    namespaced: true,
    state,
    getters
}
