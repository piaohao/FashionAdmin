import React from 'react';
import {formatMessage, FormattedMessage} from 'umi/locale';
import {Calendar, Card} from 'antd';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import RenderAuthorized from '@/components/Authorized';
import {getAuthority} from '@/utils/authority';

const Authority = getAuthority();
const Authorized = RenderAuthorized(Authority);


export default () => (
  <PageHeaderWrapper>
    <Card bordered={false}>
      <Calendar/>
    </Card>
  </PageHeaderWrapper>
);
