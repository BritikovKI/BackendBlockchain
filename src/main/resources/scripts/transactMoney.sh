#!/usr/bin/env bash
geth attach http://localhost:8546 << EOF
web3.personal.unlockAccount(eth.accounts[0],"4rfgt5");
web3.eth.sendTransaction({from:eth.accounts[0], to:"$1", value:100000000000000000, gasPrice: 100});
EOF