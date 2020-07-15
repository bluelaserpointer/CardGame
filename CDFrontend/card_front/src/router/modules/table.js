/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const tableRouter = {
  path: '/table',
  component: Layout,
  redirect: '/table/item-entity-table',
  name: 'Table',
  meta: {
    title: 'Table',
    icon: 'table'
  },
  children: [
    {
      path: 'phase-entity-table',
      component: () => import('@/components/table/phaseEntityPanel'),
      name: 'PhaseEntityPanel',
      meta: { title: 'Phase Entity Panel' }
    },
    {
      path: 'item-entity-table',
      component: () => import('@/components/table/itemEntityPanel'),
      name: 'ItemEntityPanel',
      meta: { title: 'Item Entity Panel' }
    },
    {
      path: 'card-entity-table',
      component: () => import('@/components/table/cardEntityPanel'),
      name: 'CardEntityPanel',
      meta: { title: 'Card Entity Panel' }
    },
    {
      path: 'player-panel',
      component: () => import('@/components/table/playerPanel'),
      name: 'PlayerPanel',
      meta: { title: 'Player Panel' }
    }
  ]
}
export default tableRouter
