# -*- coding: utf-8 -*-
# Generated by Django 1.9 on 2016-06-23 08:43
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('testCon', '0002_student'),
    ]

    operations = [
        migrations.CreateModel(
            name='Account',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('password', models.CharField(max_length=20)),
                ('username', models.CharField(max_length=20)),
            ],
        ),
    ]