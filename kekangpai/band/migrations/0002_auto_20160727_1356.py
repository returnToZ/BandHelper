# -*- coding: utf-8 -*-
# Generated by Django 1.9.1 on 2016-07-27 13:56
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('band', '0001_initial'),
    ]

    operations = [
        migrations.RenameField(
            model_name='personal',
            old_name='telphone',
            new_name='telephone',
        ),
    ]