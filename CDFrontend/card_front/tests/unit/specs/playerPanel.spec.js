const validateStub = {
  render: () => {},
  data() {
    return {
      listQuery: {
        page: 1,
        limit: 20,
        total: 0,
        sort: '+id'
      },
    }
  },
  methods: {
    getList: () => {}
  }
};

jest.unmock('axios');
import axios from 'axios';
import MockAdapter from "axios-mock-adapter";

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import PlayerPanel from '@/components/table/PlayerPanel'
import Element from 'element-ui';

const localVue = createLocalVue();
localVue.use(Element);

describe('PlayerPanel.vue', () => {
  const wrapper = shallowMount(PlayerPanel,
    {
      localVue,
      stubs: {
        'PlayerItemPanel': validateStub,
        'PlayerCardPanel': validateStub,
      }
    });

  it('Player Panel Resolves Rest', () => {
    wrapper.vm.handleClick({$el: {id: 'pane-second'}},{});
    wrapper.vm.handleClick({$el: {id: 'pane-third'}},{});
  });
});
