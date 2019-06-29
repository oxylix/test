const express = require('express');
const app = express();
const accountRoutes = express.Router();

// Require Account model in our routes module
let Account = require('../models/Account');

// Defined store route
accountRoutes.route('/add').post(function (req, res) {
  let account = new Account(req.body);
  account.save()
    .then(account => {
      res.status(200).json({'account': 'account in added successfully'});
    })
    .catch(err => {
    res.status(400).send("unable to save to database");
    });
});
 
// Defined get data(index or listing) route
accountRoutes.route('/').get(function (req, res) {
    Account.find(function (err, accounts){
    if(err){
      console.log(err);
    }
    else {
      res.json(accounts);
    }
  });
});

// Defined edit route
accountRoutes.route('/edit/:id').get(function (req, res) {
  let id = req.params.id;
  Account.findById(id, function (err, account){
      res.json(account);
  });
});

//  Defined update route
accountRoutes.route('/update/:id').post(function (req, res) {
    Account.findById(req.params.id, function(err, account, next) {
    if (!account)
      return next(new Error('Could not load Document'));
    else {
        account.user_id = req.body.user_id;
        account.account_name = req.body.account_name;
        account.account_email = req.body.account_email;
        account.phone_number = req.body.phone_number;
        account.address = req.body.address;
        account.zip_code = req.body.zip_code;
        account.country = req.body.country;
        account.last_modification = new Date();

        account.save().then(account => {
          res.json('Update complete');
      })
      .catch(err => {
            // console.error(err.stack);
            res.status(400).send("unable to update the database");
      });
    }
  });
});

// Defined delete | remove | destroy route
accountRoutes.route('/delete/:id').get(function (req, res) {
    Account.findByIdAndRemove({_id: req.params.id}, function(err, account){
        if(err) res.json(err);
        else res.json('Successfully removed');
    });
});

module.exports = accountRoutes;