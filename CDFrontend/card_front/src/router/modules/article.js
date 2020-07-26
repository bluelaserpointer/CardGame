/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const articleRouter = {
  path: '/article',
  component: Layout,
  redirect: '/article/list',
  name: 'article',
  meta: {
    title: 'Article',
    icon: 'edit'
  },
  children: [
    {
      path: 'activity',
      component: () => import('@/components/article/ActivityPanel'),
      name: 'Activity Panel',
      meta: { title: 'Activity Panel', icon: 'edit' }
    },
    {
      path: 'mail',
      component: () => import('@/components/article/MailPanel'),
      name: 'Mail Panel',
      meta: { title: 'Mail Panel', icon: 'edit' }
    }
  ]
};
export default articleRouter
