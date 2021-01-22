package com.quasigroup.inc

import enumeratum._

package object model {

  sealed trait DataSource extends EnumEntry

  object DataSource extends Enum[DataSource] {
    override def values: IndexedSeq[DataSource] = findValues
    case object Local extends DataSource
    case object Cache extends DataSource
    case object Persistence extends DataSource

  }

  sealed trait RDBMS extends EnumEntry

  object RDBMS extends Enum[RDBMS] {
    override def values: IndexedSeq[RDBMS] = findValues
    case object H2 extends RDBMS
    case object PG extends RDBMS
    case object MySql extends RDBMS
    case object MSSql extends RDBMS
    case object Oracle extends RDBMS
  }

  sealed trait EndPointFunction extends EnumEntry
  object EndPointFunction extends Enum[EndPointFunction] {
    override def values: IndexedSeq[EndPointFunction] = findValues
    case object Server extends EndPointFunction
    case object Client extends EndPointFunction
    case object Doc extends EndPointFunction
  }


}
