# -*- coding: utf-8 -*-
# Generated by Django 1.9 on 2016-07-13 15:36
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Account',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('username', models.CharField(max_length=20, unique=True)),
                ('password', models.CharField(max_length=40)),
            ],
        ),
        migrations.CreateModel(
            name='Personal',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('username', models.CharField(max_length=20, unique=True)),
                ('nickname', models.CharField(max_length=40)),
                ('gender', models.CharField(max_length=2)),
                ('telphone', models.CharField(max_length=20)),
                ('email', models.CharField(max_length=40)),
                ('status', models.CharField(max_length=200)),
            ],
        ),
    ]
