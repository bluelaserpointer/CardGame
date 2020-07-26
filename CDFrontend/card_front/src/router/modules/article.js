/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const articleRouter = {
  path: '/article',
  component: Layout,
  redirect: '/article/list',
  name: 'article',
  meta: {
    title: 'article',
    icon: 'el-icon-s-help'
  },
  children: [
    {
      path: 'activity',
      component: () => import('@/components/article/ActivityPanel'),
      name: 'Activity Panel',
      meta: { title: 'Activity Panel', icon: 'article' }
    },
    {
      path: 'mail',
      component: () => import('@/components/article/MailPanel'),
      name: 'Mail Panel',
      meta: { title: 'Mail Panel', icon: 'article' }
    }
  ]
};
export default articleRouter
