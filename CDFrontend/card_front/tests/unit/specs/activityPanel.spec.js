const validateStub = {
  render: () => {},
  data() {
    return {
      listQuery: {
        page: 1,
        limit: 20,
        total: 0,
        sort: '+id'
      }
    }
  },
  methods: {
    getList: () => {}
  }
}

jest.unmock('axios')
import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'

import { createLocalVue, mount, shallowMount } from '@vue/test-utils'
import ActivityPanel from '@/components/article/ActivityPanel'
import Element from 'element-ui'

const localVue = createLocalVue()
localVue.use(Element)

describe('ActivityPanel.vue', () => {
  const wrapper = shallowMount(ActivityPanel,
    {
      localVue,
      stubs: {
        'ActivityEntityPanel': validateStub
      }
    })

  it('Activity Panel Resolves Rest', () => {
    wrapper.vm.handleClick({ $el: { id: 'pane-second' }}, {})
  })
})
