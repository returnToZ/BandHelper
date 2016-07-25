# -*- coding: utf-8 -*-
# Generated by Django 1.9.1 on 2016-07-25 13:13
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('band', '0001_initial'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='account',
            name='id',
        ),
        migrations.AlterField(
            model_name='account',
            name='username',
            field=models.CharField(max_length=20, primary_key=True, serialize=False),
        ),
        migrations.AlterField(
            model_name='personal',
            name='username',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='band.Account'),
        ),
    ]
