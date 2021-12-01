#!/bin/bash
echo 'Preparing test folder:'
echo '  Cleaning test folder...'
rm -rf orders
mkdir -p orders/incoming
echo '  Copying sample data files...'
cp ../../resources/data/orders/* orders/incoming
echo 'Preparation complete!'
