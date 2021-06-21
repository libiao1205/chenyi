<template>
  <a-modal
    title="采集日志"
    :width="900"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
    @cancel="handleCancel"
  >
    <template slot="footer">
      <a-button key="back" type="primary" @click="handleCancel">
        关闭
      </a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <div style="width: 100%;max-height: 70vh;overflow-y:auto">
        <p :key="random" v-html="log"></p>
      </div>

<!--      <a-icon  type="loading"></a-icon>-->
    </a-spin>
  </a-modal>
</template>
<script>
  import { getGatherLog } from '@/api/modular/main/dataGather'

  export default {
    data () {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 15 }
        },
        visible: false,
        confirmLoading: false,
        log: '',
        timer: null,
        random: '1'
      }
    },
    methods: {
      // 初始化方法
      show(record, log) {
        this.log = log.data
        const _this = this
        this.timer = setInterval(() => {
          _this.getGatherLog(record)
        }, 3000)
        this.visible = true
      },
      // 获取采集日志
      getGatherLog (record) {
        getGatherLog(record).then((res) => {
          this.log = res.data
          this.random = new Date().getTime()
        }).catch((err) => {
          this.$message.error('获取日志失败错误：' + err.message)
          clearInterval(this.timer)
        })
      },
      handleCancel () {
        this.visible = false
        clearInterval(this.timer)
        this.$emit('ok', '')
      }
    }
  }
</script>
<style  lang="less">
</style>
