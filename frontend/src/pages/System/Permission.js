import React, {PureComponent} from 'react';
import {formatMessage, FormattedMessage} from 'umi/locale';
import {Card, Table, Icon, Switch, Badge} from 'antd';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import {connect} from 'dva';

@connect(({permission, loading}) => ({
  permission,
  loading: loading.models.permission,
}))
export default class Permission extends PureComponent {

  columns = [{
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
    width: '12%',
  }, {
    title: '编码',
    dataIndex: 'code',
    key: 'code',
  }, {
    title: '父编码',
    dataIndex: 'parentCode',
    key: 'parentCode',
  }, {
    title: '名称',
    dataIndex: 'name',
    key: 'name',
  }, {
    title: '图标',
    dataIndex: 'icon',
    key: 'icon',
    render: function (text, record, index) {
      return (
        <Icon type={text}/>
      );
    }
  }, {
    title: '路径',
    dataIndex: 'url',
    key: 'url',
  }, {
    title: '层级',
    dataIndex: 'level',
    key: 'level',
    render: function (text, record, index) {
      const color = text === 1 ? '#52c41a' : '#faad14';
      return <Badge count={text} style={{backgroundColor: color}}/>;
    }
  }, {
    title: '排序',
    dataIndex: 'priority',
    key: 'priority',
    render: function (text, record, index) {
      return <Badge count={text} style={{backgroundColor: '#52c41a'}}/>;
    }
  }, {
    title: '是菜单',
    dataIndex: 'isMenu',
    key: 'isMenu',
    render: function (text, record, index) {
      return <Switch checkedChildren="是" unCheckedChildren="否" defaultChecked={text === 1} disabled/>;
    }
  }];

  componentDidMount() {
    this.props.dispatch({
      type: 'permission/fetch',
    });
  }

  render() {
    const {permission} = this.props;
    return (
      <PageHeaderWrapper>
        <Card bordered={false}>
          <Table columns={this.columns} dataSource={permission} bordered={false}/>
        </Card>
      </PageHeaderWrapper>
    );
  }
};

